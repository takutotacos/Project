Rails.application.routes.draw do
  namespace :api, { format: 'json' } do
    resources :users
    resources :postings
  end
end
