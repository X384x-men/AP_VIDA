export interface InputTextProps {
  placeholder: string;
  messageRequired?: string;
  messageValidInput?: string;
  hint: string;
  type: string;
  required?: boolean;
  disabled?: boolean;
}

export interface InputJsonMap {
  property: string;
  textProps: InputTextProps;
  val: any;
}
export interface Button {
  props: ButtonProps;
}
interface ButtonProps {
  name: string;
}
export interface Div {
  props: DivProps;
}

interface DivProps {
  title: string;
  display: boolean;
}
export interface InputValue {
  value: string;
  path: string;
}
