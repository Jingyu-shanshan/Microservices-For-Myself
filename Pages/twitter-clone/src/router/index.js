import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../components/LoginView.vue';
import RegisterView from '../components/RegisterView.vue';
import ProfileView from '../components/ProfileView.vue';
import EditProfileView from '../components/EditProfileView.vue';
import PostView from '../components/PostView.vue';
import CreatePostView from '../components/CreatePostView.vue';
import MyPostView from '../components/MyPostView.vue';

const routes = [
  {
    path: '/login',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/register',
    name: 'RegisterView',
    component: RegisterView
  },
  {
    path: '/profile',
    name: 'ProfileView',
    component: ProfileView
  },
  {
    path: '/edit-profile',
    name: 'EditProfileView',
    component: EditProfileView
  },
  {
    path: '/posts',
    name: 'PostView',
    component: PostView
  },
  {
    path: '/my/posts',
    name: 'MyPostView',
    component: MyPostView
  },
  {
    path: '/create-post',
    name: 'CreatePostView',
    component: CreatePostView
  }
];

const router = createRouter({
  history: createWebHistory('/vue-app/'),
  routes
});

export default router;