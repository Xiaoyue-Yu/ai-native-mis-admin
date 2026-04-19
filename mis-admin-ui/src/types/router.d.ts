import "vue-router";

declare module "vue-router" {
  interface RouteMeta {
    title?: string;
    /** Allow access without login */
    public?: boolean;
  }
}
