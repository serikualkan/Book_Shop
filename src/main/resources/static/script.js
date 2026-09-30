let books = [];
let filteredBooks = [];
let cart = [];


// =========================
// LOAD BOOKS
// =========================

async function loadBooks() {

    const loading = document.getElementById("loading");
    const booksGrid = document.getElementById("booksGrid");

    try {

        const response = await fetch("/shop/books");

        if (!response.ok) {
            throw new Error("Failed to load books");
        }

        books = await response.json();

        filteredBooks = [...books];

        loading.classList.add("hidden");

        createCategories();
        renderBooks();

    } catch (error) {

        console.error(error);

        loading.innerHTML = `
            <div style="font-size:40px">⚠️</div>
            <p>Unable to load books.</p>
            <small>Please check that Spring Boot is running.</small>
        `;
    }
}


// =========================
// RENDER BOOKS
// =========================

function renderBooks() {

    const grid = document.getElementById("booksGrid");
    const noResults = document.getElementById("noResults");
    const counter = document.getElementById("bookCounter");

    grid.innerHTML = "";

    counter.textContent =
        `${filteredBooks.length} book${filteredBooks.length !== 1 ? "s" : ""}`;

    if (filteredBooks.length === 0) {

        noResults.classList.remove("hidden");

        return;
    }

    noResults.classList.add("hidden");

    filteredBooks.forEach(book => {

        const card = document.createElement("div");

        card.className = "book-card";

        const image = book.imageUrl &&
        !book.imageUrl.includes("example.com")
            ? `
                <img
                    src="${book.imageUrl}"
                    alt="${escapeHtml(book.title)}"
                    onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';"
                >
                <div class="placeholder-cover" style="display:none">
                    ${escapeHtml(book.title)}
                </div>
              `
            : `
                <div class="placeholder-cover">
                    ${escapeHtml(book.title)}
                </div>
              `;

        card.innerHTML = `

            <div class="book-image">
                ${image}
            </div>

            <div class="book-info">

                <div class="book-genre">
                    ${escapeHtml(book.genre || "Book")}
                </div>

                <div class="book-title">
                    ${escapeHtml(book.title)}
                </div>

                <div class="book-author">
                    ${escapeHtml(book.author)}
                </div>

                <div class="book-bottom">

                    <div class="price">
                        ${formatPrice(book.price)}
                    </div>

                    <button
                        class="add-button"
                        onclick="addToCart(${book.id})"
                    >
                        + Add
                    </button>

                </div>

            </div>
        `;

        grid.appendChild(card);
    });
}


// =========================
// CATEGORIES
// =========================

function createCategories() {

    const categoryList =
        document.getElementById("categoryList");

    const genres = [
        ...new Set(
            books
                .map(book => book.genre)
                .filter(Boolean)
        )
    ];

    genres.forEach(genre => {

        const button =
            document.createElement("button");

        button.className = "category";

        button.textContent = genre;

        button.onclick = function () {
            filterCategory(genre, this);
        };

        categoryList.appendChild(button);
    });
}


function filterCategory(category, button) {

    document
        .querySelectorAll(".category")
        .forEach(item =>
            item.classList.remove("active")
        );

    button.classList.add("active");

    if (category === "All") {

        filteredBooks = [...books];

    } else {

        filteredBooks =
            books.filter(book =>
                book.genre === category
            );
    }

    renderBooks();

    document
        .getElementById("books")
        .scrollIntoView({
            behavior: "smooth"
        });
}


// =========================
// SEARCH
// =========================

function searchBooks() {

    const query =
        document
            .getElementById("searchInput")
            .value
            .toLowerCase()
            .trim();

    filteredBooks = books.filter(book => {

        return (
            book.title?.toLowerCase().includes(query) ||
            book.author?.toLowerCase().includes(query) ||
            book.genre?.toLowerCase().includes(query)
        );

    });

    renderBooks();
}


function clearSearch() {

    document.getElementById("searchInput").value = "";

    filteredBooks = [...books];

    renderBooks();
}


function focusSearch() {

    document
        .getElementById("searchInput")
        .focus();

    document
        .getElementById("searchInput")
        .scrollIntoView({
            behavior: "smooth"
        });
}


// =========================
// CART
// =========================

function addToCart(id) {

    const book = books.find(book => book.id === id);

    if (!book) {
        return;
    }

    cart.push(book);

    updateCart();

    openCart();
}


function removeFromCart(index) {

    cart.splice(index, 1);

    updateCart();
}


function updateCart() {

    document.getElementById("cartCount")
        .textContent = cart.length;

    const container =
        document.getElementById("cartItems");

    container.innerHTML = "";

    if (cart.length === 0) {

        container.innerHTML = `
            <div style="
                text-align:center;
                padding:60px 10px;
                color:#777;
            ">
                <div style="font-size:45px">🛒</div>
                <p>Your cart is empty.</p>
            </div>
        `;

    } else {

        cart.forEach((book, index) => {

            const item =
                document.createElement("div");

            item.className = "cart-item";

            item.innerHTML = `

                <div>

                    <div class="cart-item-title">
                        ${escapeHtml(book.title)}
                    </div>

                    <div class="cart-item-author">
                        ${escapeHtml(book.author)}
                    </div>

                    <button
                        class="remove-item"
                        onclick="removeFromCart(${index})"
                    >
                        Remove
                    </button>

                </div>

                <div class="cart-item-price">
                    ${formatPrice(book.price)}
                </div>
            `;

            container.appendChild(item);
        });
    }

    const total =
        cart.reduce(
            (sum, book) => sum + Number(book.price),
            0
        );

    document.getElementById("cartTotal")
        .textContent = formatPrice(total);
}


function openCart() {

    document
        .getElementById("cartOverlay")
        .classList.remove("hidden");

    updateCart();
}


function closeCart() {

    document
        .getElementById("cartOverlay")
        .classList.add("hidden");
}


function checkout() {

    if (cart.length === 0) {

        alert("Your cart is empty.");

        return;
    }

    alert(
        "Checkout is not connected yet. " +
        "The cart is working correctly!"
    );
}


// =========================
// HELPERS
// =========================

function formatPrice(price) {

    return Number(price)
        .toLocaleString("ru-RU") + " ₸";
}


function escapeHtml(value) {

    if (!value) {
        return "";
    }

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}


// =========================
// START
// =========================

document.addEventListener(
    "DOMContentLoaded",
    loadBooks
);