Rails.application.routes.draw do
  namespace :api, { format: 'json' } do
    resources :users
    resources :postings
    post 'authenticate', to: 'authentication#authenticate'
    post 'user_authenticate', to: 'users#authenticate'
  end
end
