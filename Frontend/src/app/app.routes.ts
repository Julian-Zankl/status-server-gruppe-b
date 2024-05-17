import {Routes} from '@angular/router';
import {StatusGetPage} from "./routes/get/get.page";
import {StatusDeletePage} from "./routes/delete/delete.page";
import {StatusAddPage} from "./routes/add/add.page";
import {StatusUpdatePage} from "./routes/update/update.page";

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'add'
  },
  {
    path: 'add',
    component: StatusAddPage
  },
  {
    path: 'get',
    component: StatusGetPage
  },
  {
    path: 'delete',
    component: StatusDeletePage
  },
  {
    path: 'update',
    component: StatusUpdatePage
  },
  {
    path: '**',
    component: StatusAddPage
  }
];
