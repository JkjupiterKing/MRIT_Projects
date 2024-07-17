// Load navBar
$(document).ready(function() {
    $('#mySidenav').load('../common/sidenav.html');
});

// Function to handle form submission for creating a new purchase order
document.getElementById('create-order-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Collect form data
    let newItem = document.getElementById('item').value.trim();
    let newQuantity = parseInt(document.getElementById('quantity').value.trim(), 10);
    let newSupplier = document.getElementById('supplier').value.trim();
    let newPrice = parseFloat(document.getElementById('price').value.trim());

    // Validate form data (basic validation for demo purposes)
    if (!newItem || !newQuantity || !newSupplier || !newPrice) {
        alert('Please fill in all fields.');
        return;
    }

    // Create new order object
    const newOrder = {
        item: newItem,
        quantity: newQuantity,
        supplier: newSupplier,
        price: newPrice
    };

    // POST new order data to server API
    postNewOrder(newOrder);
});

async function postNewOrder(newOrder) {
    try {
        const response = await fetch('http://localhost:8080/addPurchaseOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newOrder),
        });

        if (!response.ok) {
            throw new Error('Failed to create new order');
        }

        // Optionally handle successful response if needed
        alert('Purchase order created successfully!');

    } catch (error) {
        console.error('Error creating new order:', error);
        // Optionally show an error message to the user
        alert('Failed to create new order. Please try again.');
    }
}

// Function to show the side navigation
function openNav() {
    document.getElementById("mySidenav").style.width = "16em";
}

// Function to hide the side navigation
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

// JavaScript for handling logout button click
document.getElementById('logoutBtn').addEventListener('click', function() {
    // Redirect to login page
    window.location.href = '../../app/Login/login.html'; // Replace with your actual login page URL
});
