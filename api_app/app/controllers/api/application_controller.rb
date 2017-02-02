module Api
  class ApplicationController < ActionController::API
    include ActionController::MimeResponds
    before_action :authenticate_request
    attr_reader :current_user

    rescue_from ActiveRecord::RecordNotFound, with: :not_found
    private

    def not_found
      return api_error(status: 404, errors: 'Not Found')
    end

    def authenticate_request
      @current_user = AuthorizeApiRequest.call(request.headers).result
      render json: {error: 'Not Authorized'}, status: 401 unless @current_user
    end
  end
end
