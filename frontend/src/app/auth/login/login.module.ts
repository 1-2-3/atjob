import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { UserService } from '../user/user.service';
import { LoginComponent } from './login.component';

@NgModule({
  imports: [SharedModule],
  providers: [UserService],
  declarations: [LoginComponent],
})
export class LoginModule {}
