import * as AppActions from '../../core/actions/app-actions/app-actions';

import { LanguageTheme } from 'src/app/core/interface/app-state/app-state';

export interface Languaje {
    name: LanguageTheme;
}
export const initialAppState: Languaje = {
    name: { language: 'es' }
};

export function reducer(state = initialAppState, actions: AppActions.Actions) {
    switch (actions.type) {
        case AppActions.CHANGE_LANGUAGE:
            state.name.language = actions.payLoad;
            return state;
        case AppActions.LOAD_LANGUAGE:
            return state;
        default:
            return state;
    }
}
