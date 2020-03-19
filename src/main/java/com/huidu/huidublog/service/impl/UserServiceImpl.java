package com.huidu.huidublog.service.impl;

import com.huidu.huidublog.entity.Role;
import com.huidu.huidublog.entity.User;
import com.huidu.huidublog.enums.ResultEnum;
import com.huidu.huidublog.enums.UserRoleEnum;
import com.huidu.huidublog.exception.CheckValueException;
import com.huidu.huidublog.repository.RoleRepositoey;
import com.huidu.huidublog.repository.UserRepository;
import com.huidu.huidublog.service.UserService;
import com.huidu.huidublog.utils.MD5Utils;
import com.huidu.huidublog.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @auther huidu
 * @create 2020/2/22 15:49
 * @Description: 用户service实现
 */
@Service
public class UserServiceImpl implements UserService {
    // 注入默认的头像信息
    @Value("${comment.avatar}")
    private String USER_DEFAULT_AVATAR;

    // 注入minio相关信息
    @Value("${minio.minioUrl}")
    private String MINIO_SERVER_URL;

    @Value("${minio.username}")
    private String MINIO_USERNAME;

    @Value("${minio.password}")
    private String MINIO_PASSWORD;

    @Value("${minio.bucketName}")
    private String MINIO_BUCKET_NAME;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepositoey roleRepositoey;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveNewUser(User user) {
        // 设置用户默认信息
        user.setPassword(MD5Utils.md5(user.getPassword())); // 设置加密密码
        user.setAvatar(USER_DEFAULT_AVATAR); // 设置用户默认头像
        user.setCreateTime(new Date()); // 注册时间
        user.setUpdateTime(new Date()); // 修改信息时间
        // 设置用户角色，默认为普通用户
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepositoey.getOne(UserRoleEnum.ROLE_USER.getCode()));
        user.setRoles(roles);
        // 保存用户
        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(User dbuser, User user) {
        // 设置相应的新值
        dbuser.setNickname(user.getNickname());
        dbuser.setEmail(user.getEmail());
        dbuser.setAvatar(user.getAvatar());
        userRepository.save(dbuser);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        // 对文件进行判断
        StringBuilder fileName = new StringBuilder();
        try {
            // 校验图片
            if (file == null || file.getInputStream() == null || file.getBytes() == null || file.getBytes().length <= 0) {
                throw new CheckValueException(ResultEnum.FILE_IS_NULL.getMessage());
            }
            // 随机图片名
            String fileNamefix = MD5Utils.md5("IMAGE_" + UUID.randomUUID().toString());
            String originalFilename = file.getOriginalFilename();
            // 截取图片格式
            String fileNameEnd = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName.append(fileNamefix).append(fileNameEnd);
            String imageUrl = MinioUtils.uploadFile(file, fileName.toString(),
                    MINIO_SERVER_URL, MINIO_USERNAME, MINIO_PASSWORD, MINIO_BUCKET_NAME);
            // TODO 将图片写入缓存，当该篇博客删除时，将该博客图片缓存全部删除
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            // 上传失败
            throw new CheckValueException(ResultEnum.FILE_UPLOAD_FIAL.getMessage());
        }
    }
}
