import { FormGroup } from '@angular/forms';
import { TreeUtil } from './tree-util';

export class CascaderUtil {
  /**
   * 为 formGroup 添加一个名为 cascaderValueName 的字段，值为级联下拉框要求的所有父节点Id数组
   * @param formGroup
   * @param cascaderOptions 级联下拉框数据源
   * @param propValueName 要提交给后端的单个Id属性名
   * @param cascaderValueName 要添加的给级联下拉框用的含有所有父节点Id的数组的属性名
   * @param idName 树节点Id属性名
   */
  static propValueToCascaderValue(formGroup: FormGroup, cascaderOptions, propValueName, cascaderValueName, idName) {
    if (formGroup.get(propValueName).value) {
      const node = TreeUtil.findNodeById(cascaderOptions, formGroup.get(propValueName).value, idName);
      const patchValues = {};
      patchValues[cascaderValueName] = TreeUtil.getParentIdList(node, idName);
      formGroup.patchValue(patchValues);
    }
  }

  /**
   * 将数组形式的名为 cascaderValueName 的级联下拉框值只保留最后一个元素放置到名为 propValueName 的字段中
   * @param formGroup
   * @param propValueName
   * @param cascaderValueName
   */
  static cascaderValueToPropValue(formGroup: FormGroup, propValueName, cascaderValueName) {
    const patchValues = {};
    patchValues[propValueName] =
      formGroup.get(cascaderValueName).value[formGroup.get(cascaderValueName).value.length - 1] || null;
    formGroup.patchValue(patchValues);
  }
}
