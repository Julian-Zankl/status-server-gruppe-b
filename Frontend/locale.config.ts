import {MatDateFormats} from "@angular/material/core";

export const DATE_FORMAT: MatDateFormats = {
    parse: {
        dateInput: 'LL',
    },
    display: {
        dateInput: 'DD.MM.YYYY',
        monthYearLabel: 'YYYY',
        dateA11yLabel: 'LL',
        monthYearA11yLabel: 'YYYY',
    },
};
