/**
 * 物料分类
 * @param categoris
 * @param allVisible 全部可见
 * @param maxLen
 * @returns {*}
 */
function categoryExtract(categoris, maxLen) {
    if (s) {
        categoris = categoris.substring(0, s.length - 2)
        var array = categoris.split('>>,').reverse()

        if (maxLen && array.length > maxLen) {
            array.splice(maxLen - 1, array.length - maxLen)
        }

        return array.join('/')
    }
    return categoris
}