import {
  ActionReducerMap, createSelector
} from '@ngrx/store';

import * as fromLayout from '../reducers/app-reducer/app-reducer';
import { LanguageTheme } from '../core/interface/app-state/app-state';

export interface Language {
  language: fromLayout.Languaje;
}


export const reducers: ActionReducerMap<Language> = {
  language: fromLayout.reducer
};

export const languaje = ((state): fromLayout.Languaje => state.language);
export const getActiveLanguaje = createSelector(languaje, (state): LanguageTheme => state.name);
