# Excel

Excel 中的图片没有和单元格绑定，poi 读取图片的顺序是按插入顺序，所以基本无法实现使用 excel 导入图片给指定的对象。

- .xls - HSSFWorkBook - 最大行数 65536
- .xlsx - XSSFWorkBook - 最大行数 1048576
- .xlsx - SXSSFWorkBook - 最大行数 1048576 - 硬盘缓存

# Zip

ZipInputStream 读取 zip 包时，entry 文件名影响读取

- Windows 创建的压缩包，使用 `GBK` 编码读取
- Linux / Mac 创建的压缩包，使用 `UTF-8` （默认）编码读取