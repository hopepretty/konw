package com.pc;

import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import com.pc.bean.ProtobufUtil;
import com.pc.bean.SharedbikeArea;
import com.pc.bean.SharedbikeAreaOuterClass;

import java.util.Map;

/**
 * protobuf测试
* @author pc
 * @Date 2020/10/12
 **/
public class Test {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        //1、创建一个build
        SharedbikeAreaOuterClass.SharedbikeArea.Builder sharedbikeArea = SharedbikeAreaOuterClass.SharedbikeArea.newBuilder();
        //2、设置对象属性值
        sharedbikeArea.setAreaId(22);
        sharedbikeArea.setLat(113.22D);
        sharedbikeArea.setLng(130.22D);
        sharedbikeArea.setName("xxx");
        sharedbikeArea.setType(1);
        sharedbikeArea.setSize(2.3D);
        sharedbikeArea.setMaxNum(10);
        sharedbikeArea.setNum(11);
        sharedbikeArea.setRemark("xxx");
        //3、获取一个实例
        SharedbikeAreaOuterClass.SharedbikeArea sharedbikeAreaInstance = sharedbikeArea.build();
        //4、序列化数据
        byte[] bytes = sharedbikeAreaInstance.toByteArray();
        System.out.println("序列化数据：" + bytes);

        //5、反序列化
        SharedbikeAreaOuterClass.SharedbikeArea sharedbikeArea1 = SharedbikeAreaOuterClass.SharedbikeArea.parseFrom(bytes);
        Map<Descriptors.FieldDescriptor, Object> allFields = sharedbikeArea1.getAllFields();
        SharedbikeArea sharedbikeArea2 = ProtobufUtil.copyProp(allFields, SharedbikeArea.class);
        System.out.println(sharedbikeArea2.getAreaId());
    }

}
