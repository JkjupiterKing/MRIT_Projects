document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');

    registerForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // Prevent the form from submitting via the browser

        // Collect form data
        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            email: document.getElementById('email').value,
            department: document.getElementById('department').value,
            position: document.getElementById('position').value,
            hireDate: document.getElementById('hireDate').value,
            birthDate: document.getElementById('birthDate').value,
            password: document.getElementById('password').value, 
            address: document.getElementById('address').value
        };

        // Send data to backend API
        try {
            const response = await fetch('http://localhost:8080/employees/addEmployee', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error('Failed to register user');
            }

            const responseData = await response.json();
            console.log('User registered successfully:', responseData);

            // Show success modal
            $('#successModal').modal('show');

            // Optionally, redirect to login page after a delay
            setTimeout(() => {
                window.location.href = '../../app/Login/login.html';
            }, 3000); // Redirect after 3 seconds (adjust as needed)

        } catch (error) {
            console.error('Error registering user:', error.message);
            // Handle error: display error message to user
        }
    });
});
