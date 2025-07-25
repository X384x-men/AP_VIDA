export enum REQUEST_METHODS {
  GET, POST, PUT, DELETE
}
export interface HttpResource {
  url: string;
  params?: any;
  navigate?: Navigated;
  previousUrl?: string;
  method?: REQUEST_METHODS;
  table?: UrlTable[];
}
interface UrlTable {
  params: TableParams[];
  url: string;
  column: string;
}
export interface TableParams {
  paramName: string;
  objValue: string;
}
export interface Navigated {
  nextUrl: string;
  method: REQUEST_METHODS;
  params?: any;
}

