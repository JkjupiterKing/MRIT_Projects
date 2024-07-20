document.addEventListener('DOMContentLoaded', function() {
    var loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', async function(event) {
            event.preventDefault(); // Prevent form submission
            
            var emailInput = document.getElementById('email');
            var passwordInput = document.getElementById('password');
            
            var email = emailInput.value.trim();
            var password = passwordInput.value.trim();
            
            if (email !== '' && password !== '') {
                try {
                    // Fetch all employees from backend API
                    const response = await fetch('http://localhost:8080/employees', {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });

                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }

                    const allEmployees = await response.json();

                    // Find the employee with matching email
                    const employee = allEmployees.find(emp => emp.email === email);

                    if (!employee) {
                        throw new Error('No employee found with this email');
                    }

                    const storedPasswordHash = employee.password; // Assuming password hash is fetched from API response

                    // Decode stored password hash
                    const decodedPassword = decodePasswordHash(storedPasswordHash);

                    // Log fetched data and password for debugging
                    console.log('Fetched employee data:', employee);
                    console.log('Decoded password:', decodedPassword);

                    // Verify the decoded password with user input password and email
                    if (password === decodedPassword && email === employee.email) {
                        console.log('Login successful:', employee);

                        // Save user info in localStorage or session storage if needed
                        localStorage.setItem('currentUser', JSON.stringify(employee));
                        
                        // Redirect to home page upon successful login
                        window.location.href = '../../app/dashboard/dashboard.html';
                    } else {
                        throw new Error('Login failed. Incorrect email or password.');
                    }
                } catch (error) {
                    console.error('Error logging in:', error.message);
                    alert('Login failed. Please check your credentials and try again.');
                }
            } else {
                alert('Please enter both email and password.');
            }
        });
    } else {
        console.error('Could not find loginForm element.');
    }
});

// Function to decode password hash using Base64 (for demonstration)
function decodePasswordHash(passwordHash) {
    try {
        // Replace with your own password decoding logic
        const decodedPassword = atob(passwordHash); // Decode Base64
        return decodedPassword;
    } catch (error) {
        console.error('Error decoding password hash:', error);
        return null;
    }
}
