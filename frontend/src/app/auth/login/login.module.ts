import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { LoginComponent } from './login.component';

@NgModule({
  imports: [SharedModule],
  providers: [],
  declarations: [LoginComponent],
})
export class LoginModule {}
