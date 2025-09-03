import request from "@/utils/request"

//列表查询
export const queryContents = (id) => {
    if (id) {
        return request.get(`/contents/${id}`);
    }
    return request.get('/contents');
};

export const queryImageById = (id) => {
    if(id){
        return request.get(`/images/${id}`);
    }
    return request.get('/images');

};

export const getUserById = (authorAccount) =>{
    if(authorAccount){
        return request.get(`/users/${authorAccount}`);
    }
    return request.get('/users');
}
export const getCardList = ()=>{
    return request.get('/getCardListContents');
}

export const getCategoriesById = (id) => {
    if(id){
        return request.get(`/categories/${id}`);
    }
    return request.get('/categories');
}


export const getCommentsById = (id) => {
    return request.get(`/getComments/${id}`);
}

export const getPersonalContents = (id) => {
    return request.get(`/contents/personal/${id}`);
}

export const getCollections = (authorAccount) =>{
    return request.get(`/getCollection/${authorAccount}`);
}

export const getContentsByCategory = (category) =>{
    return request.get(`/contents/category/${category}`);
}

export const searchContents = (words) => {
    if (words) {
        return request.get(`/contents/search/${words}`);
    }
    // 若搜索词为空，可返回空数组或提示信息
    return Promise.resolve({ data: [] });
};









