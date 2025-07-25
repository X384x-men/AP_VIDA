import { Dependency } from "../interfaces/dependency.interface";

export const ADD_DEPENDENCY_MODAL = {
  width: "800px",
  height: "250px",
  data: {
    data: "",
    id: 0,
  },
};

export const UPDATE_DEPENDENCY_MODAL = (dependency: Dependency) => {
  return {
    width: "500px",
  height: "250px",
  data: dependency
  }
}

export const DELETE_DEPENDENCY_MODAL = (dependency: Dependency) => {
  return {
    maxWidth: "500px",
    minHeight: "274px",
    data: dependency,
  }
}
