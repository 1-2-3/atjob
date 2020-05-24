export class TreeUtil {
  /**
   * 将数组形式的 data 构建为树形结构
   * @param data 数组形式的实体列表
   * @param idName Id 属性名
   * @param parentIdName 父节点Id属性名
   */
  static buildTree(data: any[], idName, parentIdName, labelName = 'name', titleName = 'name') {
    const attachChildNodes = (data: any[], node) => {
      const childNodes = data.filter((d) => d[parentIdName] === node[idName]);
      node.key = node[idName];
      node.value = node[idName];
      node.label = node[labelName];
      node.title = node[titleName];
      node.children = childNodes;
      node.isLeaf = childNodes.length <= 0;

      for (const childNode of childNodes) {
        childNode.parentNode = node;
        attachChildNodes(data, childNode);
      }
    };

    const rootNodes = data.filter((d) => d[parentIdName] == null);
    for (const rootNode of rootNodes) {
      attachChildNodes(data, rootNode);
    }

    return rootNodes;
  }

  /**
   * 获取当前节点 node 所有上级节点的 Id 列表
   * @param node
   * @param idName
   */
  static getParentIdList(node, idName): string[] {
    const appendParentId = (node, idName): string[] => {
      if (node.parentNode) {
        return [...appendParentId(node.parentNode, idName), node[idName]];
      } else {
        return [node[idName]];
      }
    };

    return appendParentId(node, idName);
  }

  /**
   * 从树中查找匹配 Id 的节点
   * @param rootNodes 树根节点列表
   * @param idValue Id 值
   * @param idName Id 属性名称
   */
  static findNodeById(rootNodes: any[], idValue, idName) {
    let result = null;
    for (const node of rootNodes) {
      if (node[idName] === idValue) {
        result = node;
      } else if (node.children && node.children.length) {
        let childNode = this.findNodeById(node.children, idValue, idName);
        if (childNode) {
          result = childNode;
        }
      }
    }
    return result;
  }
}
