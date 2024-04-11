<template>
    <nav class="navbar" role="navigation" aria-label="main navigation">
        <div class="navbar-brand">
            <router-link to="/" class="navbar-item">
                <img src="../assets/logo.png" alt="Logo" class="logo-img">
            </router-link>

            <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" @click="toggleNavbar">
                <span aria-hidden="true"></span>
                <span aria-hidden="true"></span>
                <span aria-hidden="true"></span>
            </a>
        </div>

        <div class="navbar-menu" :class="{ 'is-active': isNavbarOpen }">
            <div class="navbar-start">
                <router-link to="/profile" class="navbar-item">Profile</router-link>
                <router-link to="/posts" class="navbar-item">Posts</router-link>
                <router-link to="/my/posts" class="navbar-item">My Posts</router-link>
                <router-link to="/create-post" class="navbar-item">Create Post</router-link>
            </div>

            <div class="navbar-end">
                <div class="navbar-item">
                    <div class="buttons">
                        <router-link v-if="!isLoggedIn" to="/login" class="button is-light">Login</router-link>
                        <button v-if="isLoggedIn" @click="logout" class="button is-light">Logout</button>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</template>

<script>
export default {
    data() {
        return {
            isNavbarOpen: false,
            isLoggedIn: false
        };
    },
    created() {
        const token = localStorage.getItem('token');

        if (token) {
            this.isLoggedIn = true;
        }
    },
    methods: {
        toggleNavbar() {
            this.isNavbarOpen = !this.isNavbarOpen;
        },
        logout() {
            localStorage.removeItem("token");

            this.isLoggedIn = false;

            this.$router.push("/login");
        }
    }
};
</script>

<style scoped>
.logo-img {
    width: 50px; /* Adjust the width as needed */
    height: auto; /* Maintain aspect ratio */
}

.navbar {
    background-color: #1DA1F2;
}

.navbar-brand {
    padding: 0.5rem 1rem;
}

.navbar-burger {
    color: #fff;
}

.navbar-menu {
    background-color: #1DA1F2;
}

.navbar-item {
    color: #fff;
    margin-right: 20px;
}

.navbar-item:hover {
    background-color: #0E71C7;
}

.buttons {
    display: flex;
    align-items: center;
}

.buttons .button {
    margin-left: 10px;
}
</style>