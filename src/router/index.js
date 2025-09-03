import { createRouter, createWebHistory } from 'vue-router';
import Home from "@/view/Home.vue";
import contents from "@/components/contents.vue";
import Publish from "@/components/Publish.vue";
import LoginRegister from "@/components/loginRegister.vue";
import PersonalHome from "@/components/PersonalHome.vue"; // 引入个人主页组件
import SearchResult from "@/components/SearchResult.vue";
import Travel from "@/components/navItems/Travel.vue";
import Food from "@/components/navItems/Food.vue";
import Strategy from "@/components/navItems/Strategy.vue";
import Technology from "@/components/navItems/Technology.vue";
import Humanities from "@/components/navItems/Humanities.vue";
import Photography from "@/components/navItems/Photography.vue";
import CountyGeography from "@/components/navItems/CountyGeography.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'LoginRegister',
      component: LoginRegister
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/contents/:contentId',
      name: 'contents',
      component: contents,
      props: true
    },
    {
      path: '/publish',
      name: 'Publish',
      component: Publish

    },
    {
      path: '/personal-home',
      name: 'PersonalHome',
      component: PersonalHome
    },
    {
      path: '/search',
      name: 'SearchResult',
      component: SearchResult
    },
    {
      name: '旅游',
      path: '/travel',
      component: Travel
    },
    {
      name: '美食',
      path: '/food',
      component: Food
    },
    {
      name: '攻略',
      path: '/strategy',
      component: Strategy
    },
    {
      name: '科技',
      path: '/technology',
      component: Technology
    },
    {
      name: '人文',
      path: '/humanities',
      component: Humanities
    },
    {
      name: '摄影',
      path: '/photography',
      component: Photography
    },
    {
      name: '县志地理',
      path: '/countyGeography',
      component: CountyGeography
    }
  ]
});

export default router;
