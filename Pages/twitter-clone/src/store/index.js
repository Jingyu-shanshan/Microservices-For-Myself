import { createStore } from 'vuex';

export default createStore({
  state: {
    jsonData: null,
  },
  mutations: {
    setJsonData(state, data) {
      state.jsonData = data;
    },
  },
  actions: {
    saveJsonData({ commit }, data) {
      commit('setJsonData', data);
    },
  },
});