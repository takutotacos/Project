Rails.application.routes.draw do
  namespace :api, { format: 'json' } do
    resources :users
    resources :postings
    resources :categories
    post 'authenticate', to: 'authentication#authenticate'
    get 'postings_by_category', to: 'postings#get_postings_by_categories'
  end
end
