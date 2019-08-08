# myown
关于OrderUtil：
工作中涉及到返回的list中的pojo类，需要按照pojo类中的某一字段（名称）来排序，传到前段有序显示。
由于涉及到多个页面的多个不同类型的list排序返回，故抽取公共类进行调用。
以后的工作中可能也会碰到
没有什么技术含量，但是至少可以省下几分钟来思考中午吃什么


现支持：传入任何pojo或Map类型的list。返回相同类型的已排序好的list。


入参：  toOrder(List list,T t ,String needToOrderStr)
        list：待排序list
        t：泛型，传入对象类型
        needToOrderStr:待排序的字段名称（如pojo类中有user_name属性，传入user_name即可）
        
出参：<T> List<T>
        排序好的相同类型的list。
