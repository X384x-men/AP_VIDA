import { Input } from '@angular/core';
import { Button, Div } from 'src/app/core/interface/input-options/input';
import { ButtonView } from 'src/app/core/class/button-componet/button';

export abstract class TablePropertiesComponent extends ButtonView {
  @Input() button: Button = {
    props: {
      name: 'Alta'
    }
  };
  @Input() buttonDiv: Div = {
    props: {
      display: false,
      title: 'Alta'
    }
  };
  /**
   * Columna que tendra el estilo de link
   */
  @Input() columnLinkStyle: string;
  /**
   *Indica si se usara el link en una columna en la tabla para navegar hacia otro componente
   */
  @Input() useLink = false;
  @Input() showBackButton = true;
  initColumns: Array<DefaultColumns>;
}
export interface DefaultColumns {
  index: number;
  nombre?: string;
  rowData?: any;
}
