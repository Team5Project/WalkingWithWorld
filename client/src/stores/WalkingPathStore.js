import { defineStore } from 'pinia';

export const useWalkingPathStore = defineStore('walkingPath',{
  state: () => ({
    id:null
  }),
  actions:{
    setWalkingPathId(id) {
      this.id = id;
    }
  }

});
