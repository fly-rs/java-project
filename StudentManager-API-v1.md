### addOne

- 功能描述：向当前缓存中添加一个学生
- 参数：Student（非空非空，id字段为空，由系统生成）
- 返回值：
  - 成功：Student （生成ID后的完整对象）
  - 失败：抛出StudentExistException
- 业务规则：
  - 插入前需要确保缓存集合已经创建
  - 若缓存未初始化，自动触发加载

### addAll

- 功能描述：向当前缓存中添加所有学生
- 参数：List<Student> (非空，且集合中有效数据大于0，并且小于3000)
- 返回值：BatchInsertResult { successCount, failList, failReason }
- 异常：
  - StudentValidationException：整体验证失败 size>3000
  - StudentCacheException：缓存操作失败(暂未考虑)
- 业务规则：
  - 遇到重复ID，跳过该条继续处理
  - 返回结果包含详细的成功，失败明细

### findStudentById

- 功能描述：通过学号精准寻找
- 参数：String id(非空，长度固定为10位)
- 返回值：Optional<Student>
- 异常：StudentValidationException（ID格式错误）
- 业务规则：
  - 若缓存为空，自动从数据库加载
  - 不存在时，返回Optional.empty()

### findAll

- 功能描述：查看所有学生
- 参数：无
- 返回值：List<Student>，可能为空列表，不为null
- 异常：StudentCacheException（缓存加载失败）
- 业务规则：
  - 返回不可变列表
  - 空缓存时返回空列表

### updateStudentById

- 功能描述：修改指定学号的学生信息
- 参数：Student(非空)
- 返回值：Student （旧的学生信息）
- 异常：
  - StudentValidationException：参数校验失败
  - StudentNotFoundException：学生不存在
- 业务规则：
  - ID不可修改，其他字段全量覆盖
  - 自动更新updateTime字段

### deleteStudentById

- 功能描述：删除指定学号的学生
- 参数：String id（非空，长度10）
- 返回值：Optional<Student> （被删除的学生，不存在返回empty）
- 异常：StudentValidationException（ID校验失败）
- 业务规则：
  - 物理删除，数据移至delete_student文件