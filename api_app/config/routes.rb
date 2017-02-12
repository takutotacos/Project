Rails.application.routes.draw do
  namespace :api, { format: 'json' } do
    resources :users
    resources :postings
    resources :categories
    resources :relationships
    post 'authenticate', to: 'authentication#authenticate'
    get 'postings_by_category', to: 'postings#get_postings_by_categories'
    get 'get_own_postings', to: 'users#get_own_postings'
    get 'followers', to: 'relationships#get_followers'
    get 'followings', to: 'relationships#get_followings'
    get 'follower_numbers', to: 'relationships#get_follower_numbers'
    get 'following_numbers', to: 'relationships#get_following_numbers'
    get 'like_user_id_query', to: 'users#like_user_id_query'
    
  end
end
