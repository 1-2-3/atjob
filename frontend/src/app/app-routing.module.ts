import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AntDefaultComponent } from './layout/ant-default/ant-default.component';
import { FullscreenComponent } from './layout/fullscreen/fullscreen.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/auth/role' },
  { path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.module').then((m) => m.WelcomeModule) },
  {
    path: 'auth',
    component: AntDefaultComponent,
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'passport',
    component: FullscreenComponent,
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
