<template>
    <div class="edit-profile-container">
        <h2>Edit Profile</h2>
        <pre>{{ jsonData }}</pre>
        <div v-if="profile">
            <form @submit.prevent="saveProfile">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" v-model="profile.name">
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" v-model="profile.email">
                </div>
                <div class="form-group">
                    <label for="introduction">Introduction:</label>
                    <textarea id="introduction" v-model="profile.introduction"></textarea>
                </div>
                <div class="form-group">
                    <label for="location">Location:</label>
                    <input type="text" id="location" v-model="profile.location">
                </div>
                <div class="form-group">
                    <label for="websites">Websites:</label>
                    <input type="text" id="websites" v-model="profile.websites">
                </div>
                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth:</label>
                    <input type="date" id="dateOfBirth" v-model="profile.dateOfBirth">
                </div>
                <button type="submit">Save</button>
            </form>
        </div>
        <div v-else>
            <p>Loading...</p>
        </div>
    </div>
</template>

<script setup>
import axios from '@/auth/axios';
import { ref } from 'vue'
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const profile = ref(null);
const store = useStore();
const router = useRouter();

profile.value = store.state.jsonData;

// Method to save profile data
const saveProfile = async () => {
    try {
        // Call API to save profile data
        console.log(profile)
        const response = await axios.put('/account-service/api/account/update', profile.value, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.status === 200) {
            console.log('Profile saved successfully: ', response.data);
            router.push({ name: 'ProfileView' });
        } else {
            console.error('Failed to save profile:', response.statusText);
        }
    } catch (error) {
        console.error('Error saving profile:', error);
    }
};
</script>


<style>
.edit-profile-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border: 1px solid #e1e8ed;
    border-radius: 10px;
}

.edit-profile-container h2 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
}

.form-group {
    width: 100%;
    padding: 10px;
    font-size: 16px;
}

label {
    font-size: 18px;
    font-weight: bold;
    color: #1da1f2;
}

input[type="text"],
input[type="email"],
textarea {
    width: 94%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

button[type="submit"] {
    background-color: #1da1f2;
    color: #fff;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 5px;
    cursor: pointer;
}
</style>