import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../components/LoginView.vue';
import RegisterView from '../components/RegisterView.vue';
import ProfileView from '../components/ProfileView.vue';

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
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;