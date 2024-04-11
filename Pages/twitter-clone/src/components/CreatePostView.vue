<template>
    <div class="create-post-page">
        <div class="container">
            <div class="card">
                <div class="card-content">
                    <h2 class="title">Create Post</h2>
                    <form @submit.prevent="handleSubmit">
                        <div class="field">
                            <label class="label">Title</label>
                            <div class="control">
                                <input v-model="postData.title" class="input" type="text" placeholder="Enter title"
                                    required>
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Description</label>
                            <div class="control">
                                <textarea v-model="postData.description" class="textarea"
                                    placeholder="Enter description" required></textarea>
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Content</label>
                            <div class="control">
                                <textarea v-model="postData.content" class="textarea" placeholder="Enter content"
                                    required></textarea>
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Type</label>
                            <div class="control">
                                <input v-model="postData.type" class="input" type="text" placeholder="Enter type"
                                    required>
                            </div>
                        </div>
                        <div class="field">
                            <div class="control">
                                <button type="submit" class="button is-primary">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from '@/auth/axios';

export default {
    data() {
        return {
            postData: {
                title: '',
                description: '',
                content: '',
                type: ''
            }
        };
    },
    methods: {
        async handleSubmit() {
            try {
                await axios.post('/post-service/api/posts', this.postData);

                this.postData = {
                    title: '',
                    description: '',
                    content: '',
                    type: ''
                };

                this.$router.push("/posts");
            } catch (error) {
                console.error('Error submitting post:', error);
            }
        }
    }
};
</script>

<style scoped>
.create-post-page {
    padding: 20px;
    background-color: #f5f8fa;
}

.container {
    max-width: 600px;
    margin: 0 auto;
}

.card {
    border-radius: 12px;
    box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.08), 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card-content {
    padding: 20px;
}

.title {
    font-size: 1.5rem;
    margin-bottom: 20px;
}

.field {
    margin-bottom: 20px;
}

.label {
    font-weight: bold;
}

.input,
.textarea {
    width: 100%;
    border: none;
    border-radius: 8px;
    padding: 10px;
    font-size: 1rem;
    background-color: #fff;
    box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.2s;
}

.input:focus,
.textarea:focus {
    outline: none;
    box-shadow: inset 0 0 0 1px #1da1f2, 0 0 0 2px rgba(29, 161, 242, 0.3);
}

.button {
    width: 100%;
    border-radius: 999px;
    padding: 12px 20px;
    font-size: 1.2rem;
}
</style>