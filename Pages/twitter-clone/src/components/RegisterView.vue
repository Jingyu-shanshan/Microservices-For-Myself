<template>
  <div class="register-container">
    <h2>Register</h2>
    <form @submit.prevent="register" class="register-form">
      <div class="form-group">
        <input type="text" id="name" v-model="name" placeholder="name" class="input-field">
      </div>
      <div class="form-group">
        <input type="text" id="username" v-model="username" placeholder="Username" class="input-field">
      </div>
      <div class="form-group">
        <input type="email" id="email" v-model="email" placeholder="Email" class="input-field">
      </div>
      <div class="form-group">
        <input type="password" id="password" v-model="password" placeholder="Password" class="input-field">
      </div>
      <div class="form-group">
        <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="Confirm Password" class="input-field">
      </div>
      <button type="submit" class="submit-button">Register</button>
    </form>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      errorMessage: ''
    };
  },
  methods: {
    async register() {
      if (!this.validateForm()) {
        return;
      }
      try {
        const response = await fetch('http://localhost:8080/api/auth/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            name: this.name,
            accountNumber: this.username,
            email: this.email,
            password: this.password
          })
        });
        const data = await response.json();
        if (!response.ok) {
          // Handle registration error
          this.errorMessage = data.message;
        } else {
          // Registration successful, navigate to another page or show success message
          console.log('Registration successful:', data);
          this.$router.push({ name: 'LoginView', query: { username: this.username } });
        }
      } catch (error) {
        console.error('Error:', error);
        this.errorMessage = 'An error occurred during registration.';
      }
    },
    validateForm() {
      if (!this.username || !this.username || !this.email || !this.password || !this.confirmPassword) {
        alert('All fields are required');
        return false;
      }
      if (this.password !== this.confirmPassword) {
        alert('Passwords do not match');
        return false;
      }
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailPattern.test(this.email)) {
        alert('Invalid email format');
        return false;
      }
      return true;
    }
  }
};
</script>

<style>
.register-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
}

.register-form {
  background-color: #fff;
  border-radius: 4px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 20px;
}

.input-field {
  width: 95%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-button {
  background-color: #1da1f2;
  color: #fff;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #0c85d0;
}
</style>
