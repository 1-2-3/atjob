import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { RoleService } from './role.service';
import { RoleComponent } from './role.component';

@NgModule({
  imports: [SharedModule],
  providers: [RoleService],
  declarations: [RoleComponent],
  entryComponents: [],
})
export class RoleModule {}
