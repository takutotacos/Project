Rails.application.routes.draw do
  namespace :api, { format: 'json' } do
    resources :users
    resources :postings do
        resources :likes, only: [:create, :destroy]
        resources :comments, only: [:create, :destroy, :edit]
    end
    resources :categories
    resources :relationships
    post 'authenticate', to: 'authentication#authenticate'
    get 'postings_by_category', to: 'postings#get_postings_by_categories'
    get 'find_within_certain_distance', to: 'postings#find_within_certain_distance'
    get 'get_own_postings', to: 'users#get_own_postings'
    get 'like_user_id_query', to: 'users#like_user_id_query'
    get 'followers', to: 'relationships#get_followers'
    get 'followings', to: 'relationships#get_followings'
    get 'follower_numbers', to: 'relationships#get_follower_numbers'
    get 'following_numbers', to: 'relationships#get_following_numbers'
    get 'notifications/:id/', to: 'notifications#show'
    get 'notifications', to: 'notifications#index'
  end
end
