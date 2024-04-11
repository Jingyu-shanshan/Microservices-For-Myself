<template>
    <div v-if="dataLoaded">
        <div class="post-list">
            <div v-for="post in posts" :key="post.id" class="post">
                <div class="post-header">
                    <!-- <img :src="post.user_image" class="avatar" alt="User Avatar"> -->
                    <div class="user-info">
                        <span class="username">{{ post.account_number }}</span>
                        <span class="created-at">{{ post.created_at }}</span>
                    </div>
                    <button @click="deletePost(post.id)" class="delete-button">Delete</button>
                </div>
                <div class="post-content">
                    <h2 class="title">{{ post.title }}</h2>
                    <p class="description">{{ post.description }}</p>
                    <p class="content">{{ post.content }}</p>
                    <!-- <p class="type">Type: {{ post.type }}</p> -->
                    <div v-if="post.comments.length">
                        <h3>Comments:</h3>
                        <ul class="comments-list">
                            <li v-for="(comment, index) in post.comments" :key="index">{{ comment }}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <p>Loading...</p>
    </div>
</template>

<script setup>
import axios from '@/auth/axios';
import { ref, onMounted } from 'vue';

const posts = ref([]);
const dataLoaded = ref(false);

const deletePost = async (postId) => {
    try {
        const response = await axios.delete(`/post-service/api/posts/${postId}`);
        if (response.status === 200) {
            posts.value = posts.value.filter(post => post.id !== postId);
        } else {
            throw new Error('Failed to delete post');
        }
    } catch (error) {
        console.error('Error deleting post:', error);
    }
};

onMounted(async () => {
    try {
        const response = await axios.get('/post-service/api/posts/my');
        if (response.status != 200) {
            throw new Error('Failed to fetch user data');
        }
        const data = await response.data;
        posts.value = data

        dataLoaded.value = true;
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
});
</script>

<style scoped>
.delete-button {
    border: none;
    background: none;
    color: #ff4d4f;
    cursor: pointer;
    font-size: 1rem;
    margin-left: auto;
}

.delete-button:hover {
    color: #e10e0e;
    text-decoration: underline;
}

.post-list {
    width: 100%;
    max-width: 600px;
    margin: 0 auto;
}

.post {
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 8px;
    margin-bottom: 20px;
    padding: 20px;
}

.post-header {
    display: flex;
    align-items: center;
}

.avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
}

.user-info {
    flex-grow: 1;
}

.username {
    font-weight: bold;
}

.created-at {
    color: #666;
}

.title {
    font-size: 1.2rem;
    margin-bottom: 5px;
}

.description {
    margin-bottom: 10px;
    color: #333;
}

.content {
    color: #555;
}
</style>