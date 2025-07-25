export interface User {
  username: string;
  password: string;
  authorities?: Array<Authorities>;
  rol?: string;
}
export interface Authorities {
  authority: string;
}
export interface Rol {
  nombre: string;
  descripcion?: string;
  isNewAction?: number;
}

