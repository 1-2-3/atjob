import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ZorroSharperModule } from 'zorro-sharper';
import { STModule } from '@delon/abc/st';
import { AlainThemeModule } from '@delon/theme';

import { SHARED_ZORRO_MODULES } from './shared-zorro.module';
const THIRDMODULES = [ZorroSharperModule, STModule];

const COMPONENTS = [];
const DIRECTIVES = [];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    AlainThemeModule.forChild(),
    ...SHARED_ZORRO_MODULES,
    // third libs
    ...THIRDMODULES,
  ],
  declarations: [
    // your components
    ...COMPONENTS,
    ...DIRECTIVES,
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    AlainThemeModule,
    ...SHARED_ZORRO_MODULES,
    ...THIRDMODULES,
    // your components
    ...COMPONENTS,
    ...DIRECTIVES,
  ],
  providers: [],
  entryComponents: [],
})
export class SharedModule {}
