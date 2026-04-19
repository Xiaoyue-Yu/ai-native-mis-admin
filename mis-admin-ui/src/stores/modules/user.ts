import { defineStore } from "pinia";
import { computed, ref } from "vue";

export interface UserProfile {
  id: string;
  username: string;
  nickname?: string;
}

export const useUserStore = defineStore("user", () => {
  const profile = ref<UserProfile | null>(null);

  const isLoggedIn = computed(() => !!profile.value);

  const setProfile = (p: UserProfile | null) => {
    profile.value = p;
  };

  const reset = () => {
    profile.value = null;
  };

  return { profile, isLoggedIn, setProfile, reset };
});
