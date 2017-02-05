Rails.application.routes.draw do
  namespace :api, { format: 'json' } do
    resources :users
    resources :postings
    resources :categories
    post 'authenticate', to: 'authentication#authenticate'
  end
end
