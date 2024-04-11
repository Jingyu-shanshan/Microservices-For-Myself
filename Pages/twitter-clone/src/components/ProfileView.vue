<template>
    <div class="profile-container">
        <div v-if="dataLoaded">
            <div class="profile-header">
                <div>
                    <img v-if="profile.userImage" :src="profile.userImage" class="profile-image"
                        alt="User Image">
                    <img v-else src="../assets/logo.png" alt="Default User Image">
                </div>
                <!-- <div>
                    <img v-if="profile.userImage" :src="profile.userImage" class="background-image"
                        alt="Background Image">
                    <img v-else src="../assets/logo.png" alt="Default Background Image">
                </div> -->
                <h2>{{ profile.name }}</h2>
                <p>@{{ profile.accountNumber }}</p>
            </div>
            <div class="profile-details">
                <div class="profile-detail">
                    <h3>Email</h3>
                    <p>{{ profile.email }}</p>
                </div>
                <div class="profile-detail">
                    <h3>Introduction</h3>
                    <p>{{ profile.introduction || 'Not specified' }}</p>
                </div>
                <div class="profile-detail">
                    <h3>Location</h3>
                    <p>{{ profile.location || 'Not specified' }}</p>
                </div>
                <div class="profile-detail">
                    <h3>Websites</h3>
                    <a :href="profile.websites">{{ profile.websites || 'Not specified' }}</a>
                </div>
                <div class="profile-detail">
                    <h3>Date of Birth</h3>
                    <p>{{ profile.dateOfBirth || 'Not specified' }}</p>
                </div>
                <div class="profile-detail">
                    <h3>Date of Create</h3>
                    <p>{{ profile.dateOfCreate || 'Not specified' }}</p>
                </div>
            </div>
            <div class="profile-stats">
                <div class="profile-stat">
                    <h3>Following</h3>
                    <p>{{ profile.followingNumber }}</p>
                </div>
                <div class="profile-stat">
                    <h3>Followers</h3>
                    <p>{{ profile.followerNumber }}</p>
                </div>
            </div>
            <button class="edit-button" @click="goToEditProfile(profile)">Edit Profile</button>
        </div>
        <div v-else>
            <p>Loading...</p>
        </div>
    </div>
</template>

<script setup>
import axios from '@/auth/axios';
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const profile = ref(null);
const dataLoaded = ref(false);
const store = useStore();
const router = useRouter();

const goToEditProfile = (data) => {
    store.dispatch('saveJsonData', data);
    console.log(data)
    router.push({name: 'EditProfileView'});
}

onMounted(async () => {
    try {
        const response = await axios.get('/account-service/api/account/get');
        if (response.status != 200) {
            throw new Error('Failed to fetch user data');
        }
        const profileData = response.data;
        const jsonString = JSON.stringify(profileData);
        profile.value = JSON.parse(jsonString);

        dataLoaded.value = true;
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
});
</script>

<style>
.profile-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border: 1px solid #e1e8ed;
    border-radius: 10px;
}

.profile-header {
    text-align: center;
    height: 300px;
}

.background-image {
    width: 1%;
    height: auto; 
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}

.profile-image {
    border-radius: 50%;
    width: 120px;
    height: 120px;
    border: 3px solid #fff;
    position: absolute;
    top: 50px;
    left: calc(50% - 60px);
}

.profile-header h2 {
    margin-top: 160px;
    font-size: 24px;
    font-weight: bold;
}

.profile-header p {
    font-size: 16px;
    color: #657786;
}

.profile-details {
    margin-top: 20px;
}

.profile-detail {
    margin-bottom: 10px;
}

.profile-detail h3 {
    font-size: 18px;
    font-weight: bold;
    color: #1da1f2;
}

.profile-detail p {
    font-size: 16px;
    color: #14171a;
}

.profile-stats {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
}

.profile-stat {
    text-align: center;
}

.profile-stat h3 {
    font-size: 18px;
    font-weight: bold;
    color: #1da1f2;
}

.edit-button {
    background-color: #1da1f2;
    color: #fff;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 5px;
    cursor: pointer;
}

.edit-button:hover {
    background-color: #0e71c8;
}
</style>