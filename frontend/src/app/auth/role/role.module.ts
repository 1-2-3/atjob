import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { RoleService } from './role.service';
import { RoleComponent } from './role.component';
import { RoleEditComponent } from './edit/role-edit.component';

@NgModule({
  imports: [SharedModule],
  providers: [RoleService],
  declarations: [RoleComponent, RoleEditComponent],
  entryComponents: [RoleEditComponent],
})
export class RoleModule {}
