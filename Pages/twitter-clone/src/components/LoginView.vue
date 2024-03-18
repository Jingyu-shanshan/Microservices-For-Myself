<template>
    <div class="login-page">
      <div class="login-container">
        <h1>Login</h1>
        <!-- Login form -->
        <form @submit.prevent="login">
          <input type="text" placeholder="Username" v-model="username">
          <input type="password" placeholder="Password" v-model="password">
          <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <router-link to="/register">Register</router-link></p>
        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        username: '',
        password: '',
        errorMessage: ''
      };
    },
    created() {
      this.username = this.$route.query.username || '';
    },
    methods: {
      async login() {
        try {
          const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              accountNumberOrEmail: this.username,
              password: this.password
            })
          });
          const data = await response.json();
          
          // Check if login was successful
          if (response.ok) {
            // Store authentication token or session information
            localStorage.setItem('token', data.accessToken);
            // Redirect to the dashboard or other protected page
            this.$router.push({ name: 'ProfileView' });
          } else {
            // Handle login failure
            console.error('Login failed:', data.message);
            this.errorMessage = data.message || 'Login failed';
          }
        } catch (error) {
          console.error('Error:', error);
          this.errorMessage = 'An error occurred during login.';
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .login-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  
  .login-container {
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f9f9f9;
    max-width: 400px;
    width: 100%;
  }
  
  .login-container h1 {
    margin-top: 0;
  }
  
  .login-container form {
    display: flex;
    flex-direction: column;
  }
  
  .login-container input {
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 3px;
  }
  
  .login-container button {
    padding: 8px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
  }
  
  .login-container button:hover {
    background-color: #0056b3;
  }
  
  .login-container p {
    margin-top: 10px;
    text-align: center;
  }
  </style>
  