package pub.toki.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Resource
    private AuthUser authUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入授权阶段");
        String userName=request.getHeader("X-Consumer-Username");
        if(null==userName ||userName.isEmpty()) {
            response.sendError(401);
            return false;
        }
        List<String> userGroupNames= new ArrayList();
        userGroupNames.addAll(Arrays.asList(userService.getUserGroupsByName(userName)));
        if(null==userGroupNames) {
            response.sendError(401);
            return false;
        }
        KongJwtAuthAnnotation kongJwtAuthAnnotation = ((HandlerMethod) handler).getMethod().getAnnotation(KongJwtAuthAnnotation.class);
        if(kongJwtAuthAnnotation.userName().isEmpty()&& kongJwtAuthAnnotation.groupName().isEmpty()) return true;
        Boolean checkUserName=false,checkUserGroup=false;
        if(!kongJwtAuthAnnotation.userName().isEmpty()){
            String[] annotationUserNames=kongJwtAuthAnnotation.userName().split(",");
            if(checkAnnotationUser(annotationUserNames,userName)) checkUserName=true;
        }
        if(!kongJwtAuthAnnotation.groupName().isEmpty()){
            String[] annotationUserGroupNames=kongJwtAuthAnnotation.groupName().split(",");
            if(checkUserGroups(annotationUserGroupNames,userService.getUserGroupsByName(userName))) checkUserGroup=true;
        }
        if(checkUserGroup==false && checkUserName==false) {
            response.sendError(401);
            return false;
        }
        AuthUser user=authUser.getUser(userName);
        request.setAttribute("authUser",user);
        return true;
    }
    private Boolean checkUserGroups(String[] allowGroupNames,String[] userGroupNames){
        ArrayList<String> allowNamesList= new ArrayList();
        allowNamesList.addAll(Arrays.asList(allowGroupNames));
        ArrayList<String> userNamesList=new ArrayList();
        userNamesList.addAll(Arrays.asList(userGroupNames));
        userNamesList.retainAll(allowNamesList);
        if(userNamesList.size()>0){
            return true;
        } else{
            return false;
        }

    }
    private Boolean checkAnnotationUser(String[] userNames,String userName){
        Boolean haveUser=false;
        for (String annotationUserName : userNames) {
            if(userName.equals(annotationUserName)) {
                haveUser=true;
            }

        }
        return haveUser;
    }
}

