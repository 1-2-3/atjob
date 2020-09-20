import { NgModule } from '@angular/core';
import { NzLayoutModule, NzMenuModule } from 'ng-zorro-antd';
import { SharedModule } from '../shared/shared.module';
import { AntDefaultComponent } from './ant-default/ant-default.component';
import { FullscreenComponent } from './fullscreen/fullscreen.component';

const COMPONENTS = [AntDefaultComponent, FullscreenComponent];

@NgModule({
  imports: [SharedModule, NzLayoutModule, NzMenuModule],
  providers: [],
  declarations: [...COMPONENTS],
  exports: [...COMPONENTS],
})
export class LayoutModule {}
