import { DropDownMenu } from '../menu/dropdown-menu';

export const SchedulerOptions: DropDownMenu = {
    menu: [
        {
            index: 0,
            name: 'Cambiar Estatus Orden',
            icon: 'format_align_justify',
            enabled: true,
            options: [
                { index: 0, option: 'Registrada', enabled: true },
                { index: 1, option: 'Recibida', enabled: true },
                { index: 2, option: 'En Proceso', enabled: true },
                { index: 3, option: 'Instalado', enabled: true },
                { index: 4, option: 'Por cerrar', enabled: true },
                { index: 5, option: 'Reprogramacion por percance', enabled: true },
                { index: 6, option: 'Cancelada por Ausencia', enabled: true },
                { index: 7, option: 'Retraso', enabled: true },
                { index: 8, option: 'Suspendida', enabled: true },
                { index: 9, option: 'Cierre Orden', enabled: true },
            ]
        },
        {
            index: 0,
            name: 'Seguimiento de ordenes',
            icon: 'motorcycle',
            enabled: false,
            options: [
                { index: 0, option: 'Ver seguimiento de ordenes', enabled: false, icon: 'room' },
            ]
        }
        , {
            index:0,
            name: 'ReAgendamiento de Orden',
            icon: 'calendar_today',
            enabled: false,
            options: [
                { index: 0, option: 'Cambiar Horario de Orden', enabled: false},
            ]
        }
        , {
            index: 0,
            name: 'Informacion de la orden',
            icon: 'info',
            enabled: false,
            options: [
                { index: 0, icon: 'book', option: 'Informacion de la orden', enabled: false},
            ]
        },
        {
            index:0,
            name: 'Notas adicionales orden',
            icon: 'note_add',
            enabled: false,
            options: [
                { index: 0, icon: 'note', option: 'Notas adicionales de la orden', enabled: false},
            ]
        }
    ]
};
