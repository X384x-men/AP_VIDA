import { AdminUnit } from "../interfaces/admin-unit.interface";

/**
 * @description Lista de constantes para matdialogs en componente de Unidades Administrativas
 */

// Modal para agregar unidad administrativa
export const ADD_ADMIN_UNITS_MODAL = {
  width: "800px",
  height: "250px",
  data: {
    data: "",
    id: 0,
  },
};

// Modal para actualizar unidad administrativa
export const UPDATE_ADMIN_UNITS_MODAL = (adminUnit: AdminUnit) => {
  return {
    maxWidth: "500px",
    minHeight: "274px",
    data: adminUnit
  };
};

// Modal para eliminar unidad administrativa
export const DELETE_ADMIN_UNITS_MODAL = (adminUnit: AdminUnit) => {
  return {
    maxWidth: "500px",
    minHeight: "274px",
    data: adminUnit,
  }
}
