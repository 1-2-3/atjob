import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { zh_CN } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';
import { NzConfig, NZ_CONFIG } from 'ng-zorro-antd/core/config';
import { DefaultInterceptor } from './common/http/default.interceptor';
import { SharedModule } from './shared/shared.module';
import { AlainConfig, ALAIN_CONFIG } from '@delon/util';

const ngZorroConfig: NzConfig = {
  // 全局验证提示信息
  form: {
    nzAutoTips: {
      'zh-cn': {
        required: '必填项',
      },
      en: {
        required: 'Input is required',
      },
    },
  },
};

const alainConfig: AlainConfig = {
  st: {
    ps: 3,
    res: {
      reName: {
        total: 'total',
        list: 'data',
      },
      // process: (data: STData[], rawData?: any) => {
      //   return data;
      // },
    },
    req: {
      reName: {
        pi: 'pageNum',
        ps: 'pageSize',
      },
    },
    multiSort: {
      // 默认不使用多列排序，如果需要多列排序，需要在具体的 st 组件标签里增加 multiSort 属性: <st multiSort>
      global: false,
    },
    singleSort: {
      /** 请求参数名，默认：`sort` */
      key: 'sort',
      /** 列名与状态间分隔符，默认：`.` */
      nameSeparator: '.',
    },
  },
};

const INTERCEPTOR_PROVIDES = [{ provide: HTTP_INTERCEPTORS, useClass: DefaultInterceptor, multi: true }];

registerLocaleData(zh);

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SharedModule,
  ],
  providers: [
    { provide: NZ_I18N, useValue: zh_CN },
    { provide: NZ_CONFIG, useValue: ngZorroConfig },
    { provide: ALAIN_CONFIG, useValue: alainConfig },
    ...INTERCEPTOR_PROVIDES,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
