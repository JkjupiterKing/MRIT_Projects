// Load navBar
$(document).ready(function() {
  $('#mySidenav').load('../common/sidenav.html');
});

let purchaseOrders = []; // Global variable to hold purchase orders

async function fetchPurchaseOrders() {
  try {
      const response = await fetch('http://localhost:8080/getAllPurchaseOrders');
      if (!response.ok) {
          throw new Error('Failed to fetch data');
      }
      const data = await response.json();
      purchaseOrders = Array.isArray(data) ? data : []; // Assign fetched data as an array
      displayPurchaseOrders(); // Display initial set of purchase orders
  } catch (error) {
      console.error('Error fetching data:', error);
  }
}

// Function to display existing purchase orders in a table with pagination
function displayPurchaseOrders(pageNumber = 1, pageSize = 5) {
  const startIndex = (pageNumber - 1) * pageSize;
  const endIndex = startIndex + pageSize;
  const paginatedOrders = purchaseOrders.slice(startIndex, endIndex);

  let tableBodyHtml = '';
  paginatedOrders.forEach(order => {
      tableBodyHtml += `<tr>
                          <td>${order.item}</td>
                          <td>${order.quantity}</td>
                          <td>${order.supplier}</td>
                          <td>${order.price}</td>
                      </tr>`;
  });

  document.getElementById('orders-table-body').innerHTML = tableBodyHtml;

  // Pagination (assuming purchaseOrders is already populated)
  const totalPages = Math.ceil(purchaseOrders.length / pageSize);
  let paginationHtml = '';
  for (let i = 1; i <= totalPages; i++) {
      paginationHtml += `<li class="page-item ${pageNumber === i ? 'active' : ''}"><a class="page-link" href="#" onclick="displayPurchaseOrders(${i}, ${pageSize})">${i}</a></li>`;
  }
  document.getElementById('pagination').innerHTML = paginationHtml;

  // Show table container and hide form container
  document.getElementById('table-container').style.display = 'block';
  document.getElementById('form-container').style.display = 'none';
}

// Function to perform search as user types
function performSearch() {
  const searchInput = document.getElementById('searchInput').value.trim().toLowerCase();

  // Filter purchaseOrders based on search input
  const filteredOrders = purchaseOrders.filter(order => {
      const itemName = order.item.trim().toLowerCase();
      const supplierName = order.supplier.trim().toLowerCase();
      return itemName.includes(searchInput) || supplierName.includes(searchInput);
  });

  // Update table display based on filtered results
  let tableBodyHtml = '';
  filteredOrders.forEach(order => {
      tableBodyHtml += `<tr>
                          <td>${order.item}</td>
                          <td>${order.quantity}</td>
                          <td>${order.supplier}</td>
                          <td>${order.price}</td>
                      </tr>`;
  });

  document.getElementById('orders-table-body').innerHTML = tableBodyHtml;
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
  window.location.href = '/app/Login/login.html'; // Replace with your actual login page URL
});

// Initial fetch and populate table on DOMContentLoaded
document.addEventListener('DOMContentLoaded', function() {
  fetchPurchaseOrders();
});
